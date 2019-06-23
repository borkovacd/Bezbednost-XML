
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {RoomModel} from '../model/room.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class RoomService{
  private BASE_URL = 'https://localhost:8099/api/room';

  constructor(private http: HttpClient) {

  }

  createRoom(object: RoomModel, idAccomodation: any): Observable<any> {
    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/createRoom/${idAccomodation}`, body, {headers: headers});
  }

  getAllRoom(idAccomodation: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllRooms/${idAccomodation}`, {headers: headers});
  }

  checkIfReservedRoom(id: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/checkIfReservedRoom/${id}`, {headers: headers});
  }
  deleteRoom(idA: any, idR: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.delete(`${this.BASE_URL}/deleteRoom/${idA}/${idR}`, {headers: headers});
  }
  getOneRoom(idA: any, idR: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getOneRoom/${idA}/${idR}`, {headers: headers});
  }

  editRoom(object: RoomModel, idAccomodation: any, id: any): Observable<any> {
    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.put(`${this.BASE_URL}/editRoom/${idAccomodation}/${id}`, body, {headers: headers});
  }
}
