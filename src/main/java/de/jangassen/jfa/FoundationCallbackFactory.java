package de.jangassen.jfa;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class FoundationCallbackFactory {

    private static FoundationCallbackFactory INSTANCE;

    private static final String REFEREE_ID = "refereeId";
    private static final String METHOD_ONE_ARG_NAME = "callbackOneArg";
    private static final String CLASS_NAME = "FoundationCallbackHelper";

    private final ID classId;
    private final ConcurrentHashMap<Long, Consumer<ID>> callbacksOneArg = new ConcurrentHashMap<>();
    private long counter = 0;

    public static class FoundationCallback {
        private final long id;
        private final ID target;
        private final Pointer selector;

        public FoundationCallback(long id, ID target, Pointer selector) {
            this.target = target;
            this.selector = selector;
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public ID getTarget() {
            return target;
        }

        public Pointer getSelector() {
            return selector;
        }
    }

    private FoundationCallbackFactory() {
        classId = createHelperClass();
    }

    public static synchronized FoundationCallbackFactory instance() {
        if (INSTANCE == null) {
            INSTANCE = new FoundationCallbackFactory();
        }
        return INSTANCE;
    }

    private long getCallbackId(ID self, ID classId) {
        Pointer refereeId = Foundation.getInstanceVariable(classId, REFEREE_ID);
        ID ivar = Foundation.getIvar(self, refereeId);
        return ivar.longValue();
    }

    private ID createHelperClass() {
        ID classId = Foundation.allocateObjcClassPair(Foundation.getObjcClass("NSObject"), CLASS_NAME);
        Foundation.addIvar(classId, REFEREE_ID, "l");
        Foundation.registerObjcClassPair(classId);
        Foundation.addMethod(classId, Foundation.createSelector(METHOD_ONE_ARG_NAME), new Callback() {
            @SuppressWarnings("unused")
            public void callback(ID self, ID selector, ID value) {
                long callbackId = getCallbackId(self, classId);
                Consumer<ID> idConsumer = callbacksOneArg.get(callbackId);
                if (idConsumer != null) {
                    idConsumer.accept(value);
                }
            }
        }, "v@:@:@");

        return classId;
    }

    public FoundationCallback registerCallback(Consumer<ID> callback) {
        ID instance = Foundation.invoke(classId, "alloc");
        Pointer refereeId = Foundation.getInstanceVariable(classId, REFEREE_ID);

        long id = getNextId();
        Foundation.setIvar(instance, refereeId, new ID(id));
        callbacksOneArg.put(id, callback);

        return new FoundationCallback(id, instance, Foundation.createSelector(METHOD_ONE_ARG_NAME));
    }

    public void unregister(FoundationCallback callback) {
        Foundation.invoke(callback.getTarget(), "release");
        callbacksOneArg.remove(callback.getId());
    }

    private long getNextId() {
        return counter++;
    }
}
