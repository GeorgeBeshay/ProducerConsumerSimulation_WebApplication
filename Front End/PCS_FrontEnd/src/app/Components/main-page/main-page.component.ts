import { Component, OnInit } from '@angular/core';
import Konva from 'konva';
import { Stage } from 'konva/lib/Stage';
import { Layer } from 'konva/lib/Layer';
import { KonvaComponent } from 'ng2-konva';
import { MachineFormat } from 'src/app/Interfaces/machine-format';
import { FrontSystem } from 'src/app/Interfaces/front-system';
import { HttpClient } from '@angular/common/http';
import { ServerCallerService } from 'src/app/Services/server-caller.service';
@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  
  private myStage!: Stage;
  private board!: Layer;
  private machineCount=0;
  private queueCount=0;
  private systemMachines:FrontSystem={
    frontMachines:[],
  };
  private konvaMachines:Konva.Circle[]=[]; 
  private stop=false;
  private serverCaller!:ServerCallerService;
 // private machines:MachineFormat[]=[];
  constructor(private http: HttpClient) { 
    this.serverCaller=new ServerCallerService(this.http);
  }

  ngOnInit(): void {
    var stageHolder = document.querySelector('#holder');
    if (stageHolder != null) {
      this.myStage = new Konva.Stage({
        width: stageHolder?.clientWidth - 10,
        height: stageHolder?.clientHeight - 20,
        container: 'konva-holder',
      });
    }
    this.board = new Konva.Layer();
    this.myStage.add(this.board);
  }
  ////////////////////separator////////////////////
  rectangle(id:string){
  var rectangle = new Konva.Group({
    x: 25, 
    y: 25, 
    width: 40,
    height: 70,
    draggable: true,
  }); 

  rectangle.add(new Konva.Rect({
    fill: "yellow",
    height: 40,
    width: 70,
    stroke: "black",
    strokeWidth: 2,
  }));

  rectangle.add(new Konva.Text({
    text:id,
    fontSize: 18,
    fontFamily: 'Calibri',
    fill: '#000',
    width: 70,
    padding:10,
    align: 'center'
  }));
  this.board.add(rectangle);
}

////////////////////separator////////////////////
circle(id:string){
  var circle = new Konva.Group({
    x: 30, 
    y: 30, 
    width: 60,
    height: 60,
    draggable: true,
  }); 
  var c=new Konva.Circle({
    fill: "gray",
    radius: 30,
    stroke: "black",
    strokeWidth: 2,
  })
  circle.add(c);

  circle.add(new Konva.Text({
    text:id,
    fontSize: 18,
    x:-12,
    y:-15,
    fontFamily: 'Calibri',
    fill: '#000',
    align: 'center',
  }));
  this.board.add(circle);
  this.konvaMachines.push(c);
  console.log(this.konvaMachines);
  
}
////////////////////separator////////////////////
arrow(x1:any,y1:any,x2:any,y2:any){
  const a=new Konva.Arrow({
    points: [x1, y1, x2,y2],
    fill: "black",
    stroke: "black",
    strokeWidth: 2,
  })
  this.board.add(a);
  return a;
}
////////////////////separator////////////////////
createMachine(){
  this.machineCount++;
  let id="M"+this.machineCount;
  this.circle(id);
}
////////////////////separator////////////////////
createQueue(){
  let id="Q"+this.queueCount;
  this.rectangle(id);
  this.queueCount++;
}
////////////////////separator////////////////////
createArrow(){
  let x1=0;
  let y1=0;
  let x2=0;
  let y2=0;
  let i=0;
  let start:string;
  let thisExtender=this;
  this.myStage.on('click', async function (e) {
    if(i==0){
      let object = e.target;
      start=((object.getParent()).getAttr("Children")[1]).getAttr("text");
      console.log(start);
      object.getParent().setAttr("draggable",false);
      x1=thisExtender.myStage.getPointerPosition()!.x;
      y1=thisExtender.myStage.getPointerPosition()!.y;
      i++;
    }else if(i==1){
      let object = e.target;
      let terminal=((object.getParent()).getAttr("Children")[1]).getAttr("text");
      console.log(terminal);
      object.getParent().setAttr("draggable",false);
      x2=thisExtender.myStage.getPointerPosition()!.x;
      y2=thisExtender.myStage.getPointerPosition()!.y;
      i++;
      thisExtender.arrow(x1,y1,x2,y2);
      thisExtender.generateSystem(start,terminal);
      console.log(start[1],terminal[1]);
    }
  });
}
////////////////////separator////////////////////
generateSystem(obj1:string,obj2:string){
  let id1=obj1[1]
  let id2=obj2[1]
  if(obj1.includes("Q")){
    let m:MachineFormat={
      previousQueueID:Number(id1),
      machineID:Number(id2),
      nextQueueID:0,
    };
    this.systemMachines.frontMachines.push(m);
    console.log(this.systemMachines.frontMachines);
  }else{
    for(let m of this.systemMachines.frontMachines){
      if(m.machineID==Number(id1)){
        m.nextQueueID=Number(id2);
         console.log(m);
      }
    }
    console.log(this.systemMachines.frontMachines);
  }
}
////////////////////separator////////////////////
clear(){
  this.queueCount=0;
  this.machineCount=0;
  this.board.destroyChildren();
  this.konvaMachines=[];
  this.systemMachines.frontMachines=[];
  this.stop=false;
  //c
}
////////////////////separator////////////////////
async smiulate(){
  let productsNumber=Number((document.getElementById("numberOfProducts")as HTMLInputElement).value);
  console.log(this.systemMachines)
  await this.serverCaller.intialize(productsNumber,this.systemMachines);
  let flag=false;
  while(!this.stop && !flag){
    //send requ
    let colors=await this.serverCaller.update();
    flag=await this.serverCaller.finished();
    console.log(colors);
    this.color(colors);
  }
 //call back
 //this.konvaMachines[0].setAttr("fill",'#111');
}
////////////////////separator////////////////////
Stop(){
  this.stop=true
}

color(colors:string[]){
  let c=0;
  for(let m of this.konvaMachines){
    m.setAttr("fill",colors[c]);
    c++;
  }
}
// delete(){
//   let j=0;
//   let thisExtender=this;
//   this.myStage.on('click', async function (e) {
//     if(j==0){
//       let object = e.target;
//       let start=((object.getParent()).getAttr("Children")[1]).getAttr("text");
//       console.log(start);
//       (object.getParent()).delete();
//       if(start.includes("Q")){

//       }
//       j++;
//     }
//   });
// }

////////////////////separator////////////////////
replay(){

}

}
