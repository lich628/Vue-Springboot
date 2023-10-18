<template>
  <div class="pie">
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="pie1"></div>
  </div>
</template>

<style>
#pie1 {
  float: left;
  width: 100%;
  height: 100vh;
}
</style>

<script>
//引入网络请求方法
import { getList } from "@/api/chart";

// 引入基本模板
let echarts = require("echarts/lib/echarts");
// 引入饼状图组件
require("echarts/lib/chart/pie");
// 引入提示框和title组件
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");

export default {
  created() {},
  mounted() {
    this.initData();
  },
  methods: {
    //初始化数据
    initData() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById("pie1"));
      // 绘制图表
      myChart.setOption({
        title: {
          text: "物资情况", //主标题
          left: "3%",
          top: "5%",
          textStyle: {
            //标题内容的样式
            color: "#000",
            fontStyle: "normal",
            fontWeight: 10,
            fontSize: 30, //主题文字字体大小，默认为18px
          },
        },
        stillShowZeroSum: true, //鼠标滑过出现数据

        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },

        label: {
          show: false,
          position: "center",
        },

        emphasis: {
          label: {
            show: true,
            fontSize: "40",
            fontWeight: "bold",
          },
        },

        labelLine: {
          show: false,
        },

        legend: {
          top: 80, //控制图例出现的距离  默认左上角
          left: "center", //控制图例的位置
          // itemWidth: 16,//图例颜色块的宽度和高度
          // itemHeight: 12,
          textStyle: {
            //图例中文字的样式
            color: "#000",
            fontSize: 20,
          },
          //data: ["KN94口罩", "N95口罩", "防护服", "酒精消毒棉片", "防护眼罩"],
        },

        series: [
          {
            name: "物资情况",
            type: "pie",
            radius: "70%",
            center: ["50%", "60%"], //饼状图出现的位置
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      }); //end of mychart.setoption

      //fetch data by function getList
      console.log("fetchdata of piechart!");
      getList().then((res) => {
        var records = res.data.items;
        var objs = [];
        records.forEach((record) => {
          let obj = { value: record.num, name: record.name };
          objs.push(obj);
        });
        myChart.setOption({
          series: [{ data: objs }],
        });
      });

      /*窗口尺寸发生变化时，echarts 实例重置尺寸*/
      window.addEventListener("resize", function () {
        myChart.resize();
      });
    }, //end of initdata
  }, //end of method
};
</script>
  