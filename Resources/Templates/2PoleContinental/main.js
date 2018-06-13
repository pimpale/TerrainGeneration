//Fundamental stuff
var xSize = window.getWidth();
var ySize = window.getHeight();
var graphics = window.getGraphics();
//declare classes
var Math = Java.type("java.lang.Math");
var Short = Java.type("java.lang.Short");
var Integer = Java.type("java.lang.Integer");
var Long = Java.type("java.lang.Long");
var Double = Java.type("java.lang.Double");
var Thread = Java.type("java.lang.Thread");
var int_Array = Java.type("int[]");
var double_Array = Java.type("double[]");
var double_Array2D = Java.type("double[][]");
var short_Array2D = Java.type("short[][]");
var Stream = Java.type("java.util.stream.Stream");

var FastNoise = Java.type("fastnoise.FastNoise");
var NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
var FractalType = Java.type("fastnoise.FastNoise$FractalType");
var CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");
var CellularReturnType = Java.type("fastnoise.FastNoise$CellularReturnType");

var WorldUtils = Java.type("worldUtils.WorldUtils");
var OtherUtils = Java.type("worldUtils.OtherUtils");
var ValueMap2D = Java.type("worldUtils.ValueMap2D");
var Value2D = Java.type("worldUtils.Value2D");
var Kernel = Java.type("worldUtils.Kernel");

function getHeight(seed, xSize, ySize) {
	//set up the noise
	var mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.SimplexFractal); 
	var cnoise = new FastNoise(seed+2); cnoise.SetNoiseType(NoiseType.SimplexFractal); 
	var rnoise = new FastNoise(seed+3); rnoise.SetNoiseType(NoiseType.SimplexFractal); 
	//set scales for continent noise and mountain noise

	var cscale = Math.pow(2, -2);
	var mscale = Math.pow(2, -2);
	
	//weights and sizes for 
	var sizes   = [Math.pow(2, -1), Math.pow(2,  0), Math.pow(2,  1), Math.pow(2,  2), Math.pow(2,  3), Math.pow(2,  4), Math.pow(2, 5)];
	var weights = [17,              15,              13,              10,              7,               5,               3             ]; 
	var weightsum = weights.reduce(function(a, b) { return a + b; }, 0);
	
	
	var map = new ValueMap2D(xSize, ySize);
	map = map
			.stream()
			.map(function(h) {
				var x = h.getX();
				var y = h.getY();
				var cheight = cnoise.GetNoise(x*cscale, y*cscale);
				var mheight = Math.pow(1-2*Math.abs(mnoise.GetNoise(x*mscale, y*mscale)),3)-0.2;
			
				//fractal noise...
				var rheight = 0;
				for(var i = 0; i < sizes.length; i++) {
					rnoise.SetSeed(seed+2+i);
					rheight += weights[i]*rnoise.GetNoise(x*sizes[i], y*sizes[i]);
				}
				rheight = rheight/weightsum;
				
				
				var noiseSum = mheight*0.3 + rheight*0.4 + cheight*0.3;
				h.setVal(OtherUtils.clamp(noiseSum, -1, 1))	;
				return h;
			})
			.collect(ValueMap2D.getCollector());
	//map = WorldUtils.fillBasins(map,-0.2);
	return map;
}

//returns surface temperature. Temperature shall be measured in celsius
function getTemperatureMap(seed, xSize, ySize) {
	var noise = new FastNoise(seed);
	noise.SetNoiseType(NoiseType.SimplexFractal);
	noise.SetFractalOctaves(10);
	var scale = Math.pow(2,-3);
	return new ValueMap2D(xSize,ySize)
				.stream()
				.map(function(h) {
					var percentDown = h.getY()/ySize;
					var latTemp = 60*(Math.sin(Math.PI*percentDown)-0.5);
					var randTemp = 40*noise.GetNoise(x*scale,y*scale);
					h.setVal(latTemp + randTemp)
					return h;
				})
				.collector(ValueMap2D.getCollector());
			
}


//var tmap = getTemperatureMap(seed,xSize,ySize);
var smap = getHeight(seed,xSize,ySize);
smap = WorldUtils.convolve(Kernel.GAUSSIAN3, smap);
smap = smap.stream().map(function(height) {
		if(height.val < 0) {
			height.val = -1;
		}
		return height;
		})
	.collect(ValueMap2D.getCollector());
var img = smap.getImage();
while(true)
{
	Thread.sleep(20);
	graphics.drawImage(img, 0 ,0,null);
}