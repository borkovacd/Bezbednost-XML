
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

  createRoom(object: RoomModel,idAccomodation: any): Observable<any> {
    const body = JSON.stringify(object);
    return this.http.post(`${this.BASE_URL}/createRoom/${idAccomodation}`,body,httpOptions);
  }

  getAllRoom(idAccomodation: any): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllRooms/${idAccomodation}`, httpOptions);
  }

  checkIfReservedRoom(id: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.get(`${this.BASE_URL}/checkIfReservedRoom/${id}`, {headers: headers})
  }
  deleteRoom(id: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.delete(`${this.BASE_URL}/deleteRoom/` + id, {headers: headers});
  }
  getOneRoom(idA: any, idR: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.get(`${this.BASE_URL}/getOneRoom/${idA}/${idR}`, {headers: headers})
  }

  editRoom(object: RoomModel, idAccomodation: any, id: any): Observable<any> {
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put(`${this.BASE_URL}/editAccomodation/${idAccomodation}/${id}`, body, {headers: headers})
  }
}
