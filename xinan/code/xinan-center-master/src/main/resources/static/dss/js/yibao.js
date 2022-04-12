const showTopChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	let arrData = [];
	for(let dd of data){
		arrData.push({"name":dd.name, "value":(dd.value/10000).toFixed(2)});
	}
	
    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            //top: '5%',
            //left: '60%',
            show:false
        },
        color:['#4266EE', '#5BCED6'],
        series: [
            {
                name: '采购额（万元）',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        //fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: true
                },
                itemStyle:{
                    normal:{
                        label:{
                            color: 'black',
                            show:true,
                            position: 'outsize',
                            formatter: '{b}\n{d}%',
                        }
                    }
                },
                data: arrData
            }
        ]

    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

const showTopChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	let arrData = [];
	for(let dd of data){
		arrData.push({"name":dd.name, "value":(dd.value/10000).toFixed(2)});
	}
	
    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            //top: '5%',
            //left: '60%',
            show:false
        },
        color:['#2040AC','#5E78EE','#7FB1F5', '#5BCED6'],
        series: [
            {
                name: '采购额（万元）',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        //fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: true
                },
                itemStyle:{
                    normal:{
                        label:{
                            color: 'black',
                                show:true,
                                position: 'outside',
                                formatter: '{b}\n{d}%',
                        }
                    }
                },
                data: arrData
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};



const showTopChart03 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	let arrData = [];
	for(let dd of data){
		arrData.push({"name":dd.name, "value":(dd.value/10000).toFixed(2)});
	}
	
    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            //top: '5%',
            //left: '60%',
            show:false
        },
        color:['#4266EE', '#5BCED6'],
        series: [
            {
                name: '采购额（万元）',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        //fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: true
                },
                itemStyle:{
                    normal:{
                        label:{
                            color: 'black',
                                show:true,
                                position: 'outsize',
                                formatter: '{b}\n{d}%',
                        }
                    }
                },
                data: arrData
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

const showCenterChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;
	
    var value = [];
    for(let val of data.data){
    	value.push((val/10000).toFixed(2));
    }

    // 2. 指定配置和数据
    let option = {
    	tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
	        type: 'category',
	        data: dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                },
                interval: 0,
                rotate: 15,
                align: 'center',
                verticalAlign: 'top'
            }
	    },
	    yAxis: {
	    	name: '万元',
	        type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
	    },
	    series: [{
	        label: {
	            show: true,
	            position: 'top'
	        },
	        data: value,
	        type: 'bar',
            barWidth: '35',
            itemStyle: {
                color: '#4266EE'
            },
	    }]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};


const showCenterChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;
    var value = data.data;

    // 2. 指定配置和数据
    let option = {
    	tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                },
                interval: 0,
                rotate: 15,
                align: 'center',
                verticalAlign: 'top'
            }
        },
        yAxis: {
        	name: '元',
            type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [{
            label: {
                show: true,
                position: 'top'
            },
            data: value,
            type: 'bar',
            barWidth: '35',
            itemStyle: {
                color: '#4266EE'
            },
        }]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};


const showBottomChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;
	
	var value = [];
    for(let val of data.data){
    	value.push((val/10000).toFixed(2));
    }
    
    // 2. 指定配置和数据
    let option = {
    	tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                },
                interval: 0,
                rotate: 15,
                align: 'center',
                verticalAlign: 'top'
            }
        },
        yAxis: {
        	name: '万元',
            type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [{
            label: {
                show: true,
                position: 'top'
            },
            data: value,
            type: 'bar',
            barWidth: '35',
            itemStyle: {
                color: '#4266EE'
            },
        }]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};


const showBottomChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;
    var insureSumData = [];
    for(let val of data.data.insureSumData){
    	insureSumData.push((val/10000).toFixed(2));
    }
    
    var normalSumData = [];
    for(let val of data.data.normalSumData){
    	normalSumData.push((val/10000).toFixed(2));
    }
    
    var totalSumData = [];
	for(let val of data.data.totalSumData){
    	totalSumData.push((val/10000).toFixed(2));
    }
    
    var emphasisStyle = {
        itemStyle: {
            shadowBlur: 10,
            shadowColor: 'rgba(0,0,0,0.3)'
        }
    };
    // 2. 指定配置和数据
    let option = {
        legend: {
            data: ['采购总金额', '医保采购金额', '非医保采购金额'],
            top: '5%',
            right: '5%',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            data: dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                }
            }
        },
        yAxis: {
        	name: '万元',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [
        	{
                name: '采购总金额',
                type: 'bar',
                emphasis: emphasisStyle,
                data: totalSumData,
                barWidth: '35',
                itemStyle: {
                    color: '#7FB1F5'
                },
                label: {
                    show: true,
                    position: 'top'
                }
            },
            {
                name: '医保采购金额',
                type: 'bar',
                emphasis: emphasisStyle,
                data: insureSumData,
                barWidth: '35',
                itemStyle: {
                    color: '#4266EE'
                },
                label: {
                    show: true,
                    position: 'top'
                }
            },
            {
                name: '非医保采购金额',
                type: 'bar',
                emphasis: emphasisStyle,
                data: normalSumData,
                barWidth: '35',
                itemStyle: {
                    color: '#5E78EE'
                },
                label: {
                    show: true,
                    position: 'top'
                }
            },
            
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};





