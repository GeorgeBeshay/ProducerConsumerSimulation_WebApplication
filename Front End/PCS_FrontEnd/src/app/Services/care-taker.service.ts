import { Component, Injectable } from '@angular/core';
import { EnhancedStage } from '../Interfaces/enhanced-stage';
import { Stage } from 'konva/lib/Stage';
import Konva from 'konva';
import { MainPageComponent } from '../Components/main-page/main-page.component';

@Injectable({
  providedIn: 'root',
})
export class CareTakerService {
  private simulationStages: EnhancedStage[];

  constructor() {
    this.simulationStages = [];
  }

  public async saveStage(tempStage: Stage) {
    this.simulationStages.push({ stage: tempStage.toJSON(), time: Date.now() });
  }

  public getReplayStages() {
    return this.simulationStages;
  }

  public async replaySimulation(currentStage: Stage, comp: MainPageComponent) {
    for (let i = 0; i < this.simulationStages.length; i++) {
      let s = this.simulationStages[i];
      currentStage = Konva.Node.create(s.stage, 'konva-holder');
      let t1 = s.time;
      if (i < this.simulationStages.length - 1) {
        let t2 = this.simulationStages[i + 1].time;
        let time = t2 - t1;
        await new Promise((f) => setTimeout(f, time));
      }
      if (!comp.getReplayFlag()) break;
    }
  }
}
