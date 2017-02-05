package ru.crystals.utils;

import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;

public class FormDesignerUtils {
    public static void removeEvaluation(JFrame frame) {
        Stream.of(frame.getContentPane().getComponents())
            .filter(c -> c instanceof JComponent)
            .map(c -> (JComponent) c)
            .findFirst()
            .ifPresent(p -> {
                    Border b = p.getBorder();
                    if (b != null && b instanceof CompoundBorder) {
                        CompoundBorder cb = (CompoundBorder) b;
                        Border osb = cb.getOutsideBorder();
                        Border isb = cb.getInsideBorder();
                        if (osb != null && osb instanceof TitledBorder) {
                            TitledBorder tb = (TitledBorder) osb;
                            if (StringUtils.equals(tb.getTitle(), "JFormDesigner Evaluation")) {
                                Stream.of(p.getClass().getSuperclass().getDeclaredFields())
                                    .filter(f -> StringUtils.equalsIgnoreCase(f.getName(), "border"))
                                    .findFirst()
                                    .ifPresent(f -> {
                                            try {
                                                f.setAccessible(true);
                                                f.set(p, isb);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    );
                            }
                        }
                    }
                }
            );
    }
}
