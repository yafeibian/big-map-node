package yafei.big.map.node.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import yafei.big.map.node.constant.MapConstant;
import yafei.big.map.node.dao.VectorRepository;
import yafei.big.map.node.entity.dto.NodeStatus;
import yafei.big.map.node.entity.po.Vector;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.service.NodeStatusService;
import yafei.big.map.node.service.VectorService;
import yafei.big.map.node.util.DateUtils;

import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yafei.big.map.node.util.TippecanoeUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("map")
public class MapController {
    @Resource
    private VectorService vectorService;
    @Resource
    private NodeStatusService nodeStatusService;

    @PostMapping(value = "/upload")
    public String  upload(@RequestPart("file") MultipartFile file,
                        // @RequestParam("produceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date produceDate,
                         @RequestParam("projectId") String projectId) {

        //上报状态开始工作
        nodeStatusService.reportNodeWork();

        log.info("开始文件上传uuuuuuuuuuuuuuu");

        // 上传文件
        String fileName = file.getOriginalFilename();
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String basePath = System.getProperty("user.home");
        String filePath = "tippecanoe"+File.separator+ DateUtils.format(new Date());

        UUID uuid = UUID.randomUUID();
       // String inFileName = "in_"+fileName.substring(0,fileName.lastIndexOf("."))+"_"+uuid+"."+fileSuffix;
        String inFileName = "in_"+uuid+"."+fileSuffix;

        Vector vector = new Vector();
        try {
            File directory = new File(basePath + File.separator + filePath);
            if (!directory.exists()){
                directory.mkdirs();
            }
            file.transferTo(new File(basePath+ File.separator + filePath + File.separator + inFileName));
            log.info("文件上传成功={}",basePath+ File.separator + filePath + File.separator + inFileName);
            //保存文件路径

            vector.setProjectId(projectId);
            vector.setInFileName(fileName);
            vector.setInFilePath(filePath+File.separator+inFileName);
            vector.setStartTime(new Date());

        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件上传失败");
            return "文件上传失败";
        }

        //切片
        String inFilePath = basePath+File.separator + filePath + File.separator + inFileName ;
        String outFileName = "out_"+uuid+".mbtiles";
        String outFilePath = basePath+File.separator + filePath + File.separator + outFileName ;
        try {
            log.info("数据开始切片tttttttttttt");
            //TippecanoeUtil.execute("10.88.51.184","root","Kjy_1807",inFilePath,outFilePath);
            TippecanoeUtil.execute(inFilePath,outFilePath);
            log.info("数据完成切片mmmmmmmmmmmm");
            // 存下切片关系
            vector.setOutFileName(outFileName);
            vector.setOutFilePath(filePath + File.separator + outFileName);
            vector.setEndTime(new Date());
            vectorService.save(vector);
            log.info("数据关系记录成功");



        } catch (Exception e) {
            e.printStackTrace();
            return "切片失败";
        }finally {
            nodeStatusService.reportNodeFree();
        }
        return "ok";
    }

   @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        Vector vector = vectorService.get(id);
        return Result.success(vector);
    }

    @GetMapping("/fileName/{fileName}")
    public Result getByFileName(@PathVariable String fileName){
        Vector vector = vectorService.getByFileName(fileName);
        return Result.success(vector);
    }

    @GetMapping("/queryAll")
    public Result queryAll(){
        List<Vector> vectors = vectorService.queryList();
        return Result.success(vectors);
    }

}
