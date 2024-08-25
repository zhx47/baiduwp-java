package top.zhx47.baiduwp.controller.command;

import lombok.Data;

import java.util.List;

@Data
public class DownloadCommand {
    private List<String> path;
}
