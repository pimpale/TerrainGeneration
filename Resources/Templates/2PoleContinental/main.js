//Fundamental stuff
const xSize = window.getWidth();
const ySize = window.getHeight();
const graphics = window.getGraphics();
//declare classes
const Math = Java.type("java.lang.Math");
const Short = Java.type("java.lang.Short");
const Integer = Java.type("java.lang.Integer");
const Long = Java.type("java.lang.Long");
const Double = Java.type("java.lang.Double");
const Thread = Java.type("java.lang.Thread");
const int_Array = Java.type("int[]");
const double_Array = Java.type("double[]");
const double_Array2D = Java.type("double[][]");
const short_Array2D = Java.type("short[][]");
const Stream = Java.type("java.util.stream.Stream");

const FastNoise = Java.type("fastnoise.FastNoise");
const NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
const FractalType = Java.type("fastnoise.FastNoise$FractalType");
const CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");
const CellularReturnType = Java.type("fastnoise.FastNoise$CellularReturnType");

const WorldUtils = Java.type("worldUtils.WorldUtils");
const OtherUtils = Java.type("worldUtils.OtherUtils");
const HeightMap = Java.type("worldUtils.HeightMap");
const Height = Java.type("worldUtils.Height");
const HeightMapCollector = Java.type("worldUtils.HeightMapCollector");

function getHeightMap(seed, xSize, ySize) {
	//set up the noise
	let mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.SimplexFractal);// mnoise.SetFractalOctaves(8);
	let cnoise = new FastNoise(seed+2); cnoise.SetNoiseType(NoiseType.SimplexFractal);
	let rnoise = new FastNoise(seed+3); rnoise.SetNoiseType(NoiseType.SimplexFractal); rnoise.SetFractalOctaves(8);
	//set scales for continent noise and mountain noise

	let cscale = Math.pow(2, -3);
	let mscale = Math.pow(2, -2);
	let rscale = Math.pow(2, -1);
	let map = new HeightMap(xSize, ySize);
	map = map.stream()
			.map(function(h) {
				let x = h.getX();
				let y = h.getY();
				let mheight = Math.pow(1-3*Math.abs(mnoise.GetNoise(x*mscale, y*mscale)),3);
				let cheight = cnoise.GetNoise(x*cscale,y*cscale);
				let rheight = rnoise.GetNoise(x*rscale,y*rscale);
				let fheight = cheight*0.5 + rheight*0.3 + mheight*0.2 - 0.1;
				h.setVal(OtherUtils.clamp(fheight,-1,1));
				return h;
			})
			.collect(new HeightMapCollector());
	map = WorldUtils.fillBasins(map,-0.2);
	map = new HeightMap(map
			.stream()
			.map(function(h) {
				let x = h.getX();
				let y = h.getY();
				h.setVal(h.getVal() + 0.1*mnoise.GetNoise(2*x,2*y));
				return h;
			}));
	map = WorldUtils.fillBasins(map,-0.2);
	return map;
}

//returns surface temperature. Temperature shall be measured in celsius
function getTemperatureMap(seed, xSize, ySize) {
	let noise = new FastNoise(seed);
	noise.SetNoiseType(NoiseType.SimplexFractal);
	noise.SetFractalOctaves(10);
	let scale = Math.pow(2,-3);
	let map = new HeightMap(xSize, ySize);
	return new HeightMap(xSize,ySize)
				.stream()
				.map(function(h) {
					let percentDown = h.getY()/ySize;
					let latTemp = 60*(Math.sin(Math.PI*percentDown)-0.5);
					let randTemp = 40*noise.GetNoise(x*scale,y*scale);
					h.setVal(latTemp + randTemp)
					return h;
				})
				.collector(new HeightMapCollector());
			
}


//let tmap = getTemperatureMap(seed,xSize,ySize);
let smap = getHeightMap(seed,xSize,ySize);
smap = new HeightMap(smap.stream().map(function(height) {
	if(height.val < 0) {
		height.val = -1;
	}
	return height;
}));
let img = smap.getImage();
while(true)
{
	Thread.sleep(20);
	graphics.drawImage(img, 0 ,0,null);
}