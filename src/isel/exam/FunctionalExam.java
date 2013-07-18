package isel.exam;

import java.util.Iterator;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: nfma
 * Date: 18/07/13
 * Time: 03:35
 * To change this template use File | Settings | File Templates.
 */
public class FunctionalExam {
    interface Student {
        int getGrade();
        String getName();
    }

    interface Func<T, U> {
        boolean exec(T t);
        U getExecutionsResult();
    }

    public void filter(Iterator<Student> it, Func<Student, Integer> f) {
        for (;it.hasNext();) {
            final Student student = it.next();
            if(f.exec(student))
                it.remove();
        }
    }

    public float average(Collection<Student> c) {
        int all = c.size();
        int sum = 0;
        final Iterator<Student> it = c.iterator();
        for (; it.hasNext();) {
            final Student student = it.next();
            if(student.getGrade() >= 10) {
                sum += student.getGrade();
                it.remove();
            }
        }
        if (all - c.size() == 0)
            return 0;
        return sum / (float) (all - c.size());
    }

}
