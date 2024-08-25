package top.zhx47.baiduwp.common.config;

import com.baomidou.mybatisplus.extension.ddl.IDdl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class MysqlDdl implements IDdl {

    private final DataSource dataSource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        consumer.accept(dataSource);
    }

    /**
     * 获取要执行的SQL脚本文件列表
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
        );
    }
}