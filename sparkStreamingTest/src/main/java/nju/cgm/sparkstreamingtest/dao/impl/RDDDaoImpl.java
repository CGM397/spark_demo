package nju.cgm.sparkstreamingtest.dao.impl;

import nju.cgm.sparkstreamingtest.dao.RDDDao;
import nju.cgm.sparkstreamingtest.model.RecruitmentCountRes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/7 9:06
 * @description: RDDDaoImpl
 */
public class RDDDaoImpl implements RDDDao, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void insertBatch(List<RecruitmentCountRes> store) {
        try {
            Connection conn = BaseDao.getConnection();
            String sql = "replace into recruitment_count(position_class, position_count) " +
                    "values (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (RecruitmentCountRes one : store) {
                stmt.setString(1, one.getPositionClass());
                stmt.setInt(2, one.getPositionCount());
                stmt.execute();
            }
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
