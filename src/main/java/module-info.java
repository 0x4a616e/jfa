module jfa {
    requires com.sun.jna;
    exports de.jangassen.jfa;
    exports de.jangassen.jfa.foundation;
    exports de.jangassen.jfa.appkit;
    exports de.jangassen.jfa.annotation;
    exports de.jangassen.jfa.cleanup;

    opens de.jangassen.jfa to com.sun.jna;
}