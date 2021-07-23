package yafei.big.map.node.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import yafei.big.map.node.dao.VectorRepository;
import yafei.big.map.node.entity.po.Vector;
import yafei.big.map.node.feign.VectorFeign;
import yafei.big.map.node.service.VectorService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class VectorServiceImpl implements VectorService {

    @Resource
    private VectorRepository vectorRepository;

    @Resource
    private VectorFeign vectorFeign;

    @Override
    public boolean save(Vector vector) {
        vectorRepository.save(vector);
        vectorFeign.reportVectorRecord(vector);
        return true;
    }

    @Override
    public Vector get(Integer id) {
        return vectorRepository.getOne(id);
    }

    @Override
    public Vector getByFileName(String fileName) {
        Vector vector = new Vector();
        vector.setInFileName(fileName);
        Example<Vector> example = Example.of(vector);
        Optional<Vector> optional = vectorRepository.findOne(example);
        return optional.get();
    }

    @Override
    public List<Vector> queryList() {
        return vectorRepository.findAll();
    }
}
