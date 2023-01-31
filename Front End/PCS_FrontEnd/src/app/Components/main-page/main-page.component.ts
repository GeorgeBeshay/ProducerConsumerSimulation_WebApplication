import { Component, OnInit } from '@angular/core';
import Konva from 'konva';
import { Stage } from 'konva/lib/Stage';
import { Layer } from 'konva/lib/Layer';
import { MachineFormat } from 'src/app/Interfaces/machine-format';
import { FrontSystem } from 'src/app/Interfaces/front-system';
import { HttpClient } from '@angular/common/http';
import { ServerCallerService } from 'src/app/Services/server-caller.service';
import { CareTakerService } from 'src/app/Services/care-taker.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
})
export class MainPageComponent implements OnInit {
  private myStage!: Stage;
  private board!: Layer;
  private machineCount = 0;
  private queueCount = 0;
  private systemMachines: FrontSystem = {
    frontMachines: [],
  };
  private konvaMachines: Konva.Circle[] = [];
  private stop = false;
  private serverCaller!: ServerCallerService;
  private careTaker!: CareTakerService;
  private queues: Konva.Group[] = [];
  private tempC = 0;
  constructor(private http: HttpClient) {
    this.serverCaller = new ServerCallerService(this.http);
  }

  ngOnInit(): void {
    var stageHolder = document.querySelector('#holder');
    if (stageHolder != null) {
      this.myStage = new Konva.Stage({
        width: stageHolder?.clientWidth - 10,
        height: stageHolder?.clientHeight,
        container: 'konva-holder',
      });
    }
    this.board = new Konva.Layer();
    this.myStage.add(this.board);
  }
  // ---------------------- Separator ----------------------
  rectangle(id: string) {
    var rectangle = new Konva.Group({
      x: 25,
      y: 25,
      width: 40,
      height: 70,
      draggable: true,
    });

    rectangle.add(
      new Konva.Rect({
        fill: 'yellow',
        height: 40,
        width: 70,
        stroke: 'black',
        strokeWidth: 2,
      })
    );

    rectangle.add(
      new Konva.Text({
        text: id,
        fontSize: 18,
        fontFamily: 'Calibri',
        fill: '#000',
        width: 70,
        padding: 10,
        align: 'center',
      })
    );
    console.log(rectangle.getChildren());
    this.board.add(rectangle);
    this.queues.push(rectangle);
  }

  // ---------------------- Separator ----------------------
  circle(id: string) {
    var circle = new Konva.Group({
      x: 30,
      y: 30,
      width: 60,
      height: 60,
      draggable: true,
    });
    var c = new Konva.Circle({
      fill: 'gray',
      radius: 30,
      stroke: 'black',
      strokeWidth: 2,
    });
    circle.add(c);

    circle.add(
      new Konva.Text({
        text: id,
        fontSize: 18,
        x: -12,
        y: -15,
        fontFamily: 'Calibri',
        fill: '#000',
        align: 'center',
      })
    );
    this.board.add(circle);
    this.konvaMachines.push(c);
    console.log(this.konvaMachines);
  }
  // ---------------------- Separator ----------------------
  arrow(x1: any, y1: any, x2: any, y2: any) {
    const a = new Konva.Arrow({
      points: [x1, y1, x2, y2],
      fill: 'black',
      stroke: 'black',
      strokeWidth: 2,
    });
    this.board.add(a);
    return a;
  }
  // ---------------------- Separator ----------------------
  createMachine() {
    this.machineCount++;
    let id = 'M' + this.machineCount;
    this.circle(id);
  }
  // ---------------------- Separator ----------------------
  createQueue() {
    let id = 'Q' + this.queueCount;
    this.rectangle(id);
    this.queueCount++;
  }
  // ---------------------- Separator ----------------------
  createArrow() {
    let x1 = 0;
    let y1 = 0;
    let x2 = 0;
    let y2 = 0;
    let i = 0;
    let start: string;
    let thisExtender = this;
    this.myStage.on('click', async function (e) {
      if (i == 0) {
        let object = e.target;
        start = object.getParent().getAttr('Children')[1].getAttr('text');
        console.log(start);
        object.getParent().setAttr('draggable', false);
        x1 = thisExtender.myStage.getPointerPosition()!.x;
        y1 = thisExtender.myStage.getPointerPosition()!.y;
        i++;
      } else if (i == 1) {
        let object = e.target;
        let terminal = object
          .getParent()
          .getAttr('Children')[1]
          .getAttr('text');
        console.log(terminal);
        object.getParent().setAttr('draggable', false);
        x2 = thisExtender.myStage.getPointerPosition()!.x;
        y2 = thisExtender.myStage.getPointerPosition()!.y;
        i++;
        thisExtender.arrow(x1, y1, x2, y2);
        thisExtender.generateSystem(start, terminal);
        console.log(start[1], terminal[1]);
      }
    });
  }
  // ---------------------- Separator ----------------------
  generateSystem(obj1: string, obj2: string) {
    let id1 = obj1[1];
    let id2 = obj2[1];
    if (obj1.includes('Q')) {
      let m: MachineFormat = {
        previousQueueID: Number(id1),
        machineID: Number(id2),
        nextQueueID: 0,
      };
      this.systemMachines.frontMachines.push(m);
      console.log(this.systemMachines.frontMachines);
    } else {
      for (let m of this.systemMachines.frontMachines) {
        if (m.machineID == Number(id1)) {
          m.nextQueueID = Number(id2);
          console.log(m);
        }
      }
      console.log(this.systemMachines.frontMachines);
    }
  }
  // ---------------------- Separator ----------------------
  clear() {
    this.queueCount = 0;
    this.machineCount = 0;
    var stageHolder = document.querySelector('#holder');
    if (stageHolder != null) {
      this.myStage = new Konva.Stage({
        width: stageHolder?.clientWidth - 10,
        height: stageHolder?.clientHeight,
        container: 'konva-holder',
      });
    }
    this.board = new Konva.Layer();
    this.myStage.add(this.board);
    this.konvaMachines = [];
    this.systemMachines.frontMachines = [];
    this.stop = false;
    this.queues = [];
  }
  // ---------------------- Separator ----------------------
  async smiulate() {
    this.careTaker = new CareTakerService();
    let productsNumber = Number(
      (document.getElementById('numberOfProducts') as HTMLInputElement).value
    );
    await this.serverCaller.intialize(productsNumber, this.systemMachines);
    await this.careTaker.saveStage(this.myStage);
    let flag = false;
    while (!this.stop && !flag) {
      let colors = await this.serverCaller.update();
      let queuesCount = await this.serverCaller.queuesCount();
      flag = await this.serverCaller.finished();
      console.log(colors);
      console.log(queuesCount);
      this.setQueuesCount(queuesCount);
      this.color(colors);
      await this.careTaker.saveStage(this.myStage);
      console.log(this.myStage);
    }
  }
  // ---------------------- Separator ----------------------
  async Stop() {
    this.stop = true;
    await this.serverCaller.stop();
  }
  // ---------------------- Separator ----------------------
  setQueuesCount(queuesCount: number[]) {
    let c = 0;
    for (let q of this.queues) {
      q.getChildren().pop();
      q.add(new Konva.Text({
      text: `Q${c}: ${queuesCount[c]}`,
      fontSize: 18,
      fontFamily: 'Calibri',
      fill: '#000',
      width: 70,
      padding: 10,
      align: 'center',
    }))
      c++;
    }
  }
  // ---------------------- Separator ----------------------
  color(colors: string[]) {
    let c = 0;
    for (let m of this.konvaMachines) {
      m.setAttr('fill', colors[c]);
      c++;
    }
  }
  // ---------------------- Separator ----------------------
  async replay() {
    this.careTaker.replaySimulation(this.myStage);
  }
}
