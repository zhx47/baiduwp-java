package top.zhx47.baiduwp.api.baidu.pan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zhx47.baiduwp.api.baidu.pan.enums.BaiduPanCategoryEnum;

@NoArgsConstructor
@Data
public class BaiduPanFileMetaDTO {
    /**
     * 文件在云端的唯一标识ID
     */
    @JsonProperty("fs_id")
    private String fsId;

    /**
     * 文件的绝对路径
     */
    @JsonProperty("path")
    private String path;

    /**
     * 文件名称
     */
    @JsonProperty("server_filename")
    private String serverFilename;

    /**
     * 文件大小，单位B
     */
    @JsonProperty("size")
    private Long size;

    /**
     * 文件在服务器修改时间
     */
    @JsonProperty("server_mtime")
    private Integer serverMtime;

    /**
     * 文件在服务器创建时间
     */
    @JsonProperty("server_ctime")
    private Integer serverCtime;

    /**
     * 文件在客户端修改时间
     */
    @JsonProperty("local_mtime")
    private Integer localMtime;

    /**
     * 文件在客户端创建时间
     */
    @JsonProperty("local_ctime")
    private Integer localCtime;

    /**
     * 是否为目录，0 文件、1 目录
     */
    @JsonProperty("isdir")
    private Integer isdir;

    /**
     * 文件类型
     */
    @JsonProperty("category")
    private BaiduPanCategoryEnum category;

    /**
     * 云端哈希（非文件真实MD5），只有是文件类型时，该字段才存在
     */
    @JsonProperty("md5")
    private String md5;

    /**
     * 该目录是否存在子目录，只有请求参数web=1且该条目为目录时，该字段才存在， 0为存在， 1为不存在
     */
    @JsonProperty("dir_empty")
    private Integer dirEmpty;

    /**
     * 下载链接
     */
    @JsonProperty("dlink")
    private String dlink;


//        @JsonProperty("tkbind_id")
//        private Integer tkbindId;
//        @JsonProperty("real_category")
//        private String realCategory;
//        @JsonProperty("is_scene")
//        private Integer isScene;
//        @JsonProperty("owner_type")
//        private Integer ownerType;
//        @JsonProperty("wpfile")
//        private Integer wpfile;
//        @JsonProperty("unlist")
//        private Integer unlist;
//        @JsonProperty("oper_id")
//        private Integer operId;
//        @JsonProperty("extent_tinyint7")
//        private Integer extentTinyint7;
//        @JsonProperty("owner_id")
//        private Integer ownerId;
//        @JsonProperty("share")
//        private Integer share;
//        @JsonProperty("server_atime")
//        private Integer serverAtime;
//        @JsonProperty("pl")
//        private Integer pl;
//        @JsonProperty("from_type")
//        private Integer fromType;
}
