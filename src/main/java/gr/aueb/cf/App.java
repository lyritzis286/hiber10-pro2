package gr.aueb.cf;

import gr.aueb.cf.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Hello world!
 */
public class App {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(
                    "schoolPU");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            String sql = "SELECT t FROM Teacher t WHERE t.active = TRUE";

            //Criteria API Select active teachers
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
            Root<Teacher> t = cq.from(Teacher.class);

//            cq.select(t).where(cb.equal(t.get("active"), true));
            cq.select(t).where(cb.isTrue(t.get("active")));





            tx.commit();
        } catch (Exception e) {
            if ( tx.isActive()) tx.rollback();

            throw e;

        }finally {
            em.close();
            emf.close();
        }





    }
}
