<template>
  <!-- 图标 -->
  <span :style="{ width: width + 'px', height: width + 'px' }" class="icon">
    <img :src="getImage()" :style="{ 'object-fit': fit }" alt="icon"/>
  </span>
</template>

<script setup lang="ts">
import {getCurrentInstance} from "vue";

const {proxy} = getCurrentInstance();
const props = defineProps({
  category: {
    type: String,
  },
  isdir: {
    type: Number,
  },
  filename: {
    type: String,
  },
  width: {
    type: Number,
    default: 32,
  },
  fit: {
    type: String,
    default: "cover",
  },
});

const fileTypeMap = {
  0: {desc: "目录", icon: "folder"},
  1: {desc: "视频", icon: "video"},
  2: {desc: "音频", icon: "music"},
  3: {desc: "图片", icon: "image"},
  4: {desc: "文档", icon: ""},
  5: {desc: "应用", icon: "exe"},
  6: {desc: "其他", icon: ""},
  7: {desc: "种子", icon: "bt"},
};

const getImage = () => {
  let fileType = props.category
  if (props.isdir === 1) {
    fileType = 0
  }
  let icon = "unknow_icon";

  if (fileType === "4" || fileType === "6") {
    const extension = props.filename.split('.').pop().toLowerCase();
    switch (extension) {
      case 'ppt':
      case 'pptx':
        icon = 'ppt';
        break
      case 'doc':
      case 'docx':
        icon = 'word';
        break
      case 'xls':
      case 'xlsx':
        icon = 'excel';
        break
      case 'zip':
      case 'rar':
      case '7z':
      case 'tar':
      case 'gz':
      case 'bz2':
      case 'tgz':
      case 'tar.gz':
      case 'tar.bz2':
      case 'lz':
      case 'lzma':
      case 'lzh':
      case 'xz':
      case 'z':
        icon = 'zip';
        break
      default:
        icon = 'others';
    }
  } else {
    // 根据文件类型判断图标
    const iconMap = fileTypeMap[fileType];
    if (iconMap !== undefined) {
      icon = iconMap["icon"];
    }
  }

  return new URL(`/src/assets/icon-image/${icon}.png`, import.meta.url).href;
};
</script>

<style lang="scss" scoped>
.icon {
  text-align: center;
  display: inline-block;
  border-radius: 3px;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
  }
}
</style>
