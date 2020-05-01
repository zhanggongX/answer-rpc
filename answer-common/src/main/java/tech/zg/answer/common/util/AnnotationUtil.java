package tech.zg.answer.common.util;

import tech.zg.answer.common.annotation.AnswerService;

import java.util.Iterator;
import java.util.List;

public class AnnotationUtil {

    public static void validAnswerServiceAnnotation(List<Class<?>> clsList) {
        if (clsList != null && clsList.size() > 0) {
            Iterator<Class<?>> classIterator = clsList.iterator();
            while (classIterator.hasNext()) {
                Class<?> next = classIterator.next();
                AnswerService answerService = next.getAnnotation(AnswerService.class);
                if (answerService == null) {
                    classIterator.remove();
                }
            }
        }
    }
}