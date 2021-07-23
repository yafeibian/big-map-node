package yafei.big.map.node.service;

import yafei.big.map.node.entity.po.Vector;

import java.util.List;

public interface VectorService {
    boolean save(Vector vector);

    Vector get(Integer id);

    Vector getByFileName(String fileName);

    List<Vector> queryList();
}
