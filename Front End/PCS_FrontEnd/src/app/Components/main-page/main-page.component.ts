import { Component, OnInit } from '@angular/core';
import Konva from 'konva';
import { Stage } from 'konva/lib/Stage';
import { Layer } from 'konva/lib/Layer';
import { KonvaComponent } from 'ng2-konva';
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

  constructor() { }

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

  circle.add(new Konva.Circle({
    fill: "gray",
    radius: 30,
    stroke: "black",
    strokeWidth: 2,
  }));

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
  let thisExtender=this;
  this.myStage.on('click', async function (e) {
    if(i==0){
      let object = e.target;
      let start=((object.getParent()).getAttr("Children")[1]).getAttr("text");
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
    }
  });
}
////////////////////separator////////////////////
generateSystem(){
  
}
////////////////////separator////////////////////
clear(){
  this.queueCount=0;
  this.machineCount=0;
  this.board.destroyChildren();
}
////////////////////separator////////////////////
smiulate(){
 //call back
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
