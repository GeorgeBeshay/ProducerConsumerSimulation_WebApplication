import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { FrontSystem } from '../Interfaces/front-system';

@Injectable({
  providedIn: 'root'
})
export class ServerCallerService {

  private port = 8081;
  private url = `http://localhost:${this.port}/server/`;
  constructor(private http: HttpClient) {}

  async intialize(productsCount:number,systemMachines:FrontSystem){
    return await firstValueFrom(this.http.post(this.url + 'initialize/'+productsCount,systemMachines ));
  }
  async update(){
    return await firstValueFrom(this.http.post<string[]>(this.url + 'update',null));
  }
  async finished(){
    return await firstValueFrom(this.http.post<boolean>(this.url + 'finished',null));
  }
  async stop(){
    return await firstValueFrom(this.http.post(this.url + 'stop',null));
  }
}
