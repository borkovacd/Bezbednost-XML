
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {RoomModel} from "../model/room.model";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class RoomService{
  private BASE_URL = 'https://localhost:8099/api/room';

  constructor(private http: HttpClient) {

  }

  createRoom(object: RoomModel,idAccommodtion: any): Observable<any> {
    const body = JSON.stringify(object);
    return this.http.post(`${this.BASE_URL}/createRoom/${idAccommodtion}`,body,httpOptions);
  }
}
