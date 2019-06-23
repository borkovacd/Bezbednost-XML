import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SearchRoomModel} from '../model/searchRoom.model';
import {ReservationModel} from '../model/reservation.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class ReservationAgentService {
  private BASE_URL = 'https://localhost:8099/api/reservationAgent';

  constructor(private http: HttpClient) {

  }

  getAllReservationAgent(idAgent: string): Observable<any> {

    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': idAgent});
    return this.http.get(`${this.BASE_URL}/getAllReservationsAgent/${idAgent}`, {headers: headers});
  }

  searchRoom(object: SearchRoomModel): Observable<any> {
    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/searchRoom/${token}`, body, {headers: headers});
  }

  createReservationAgent(object: ReservationModel, idRoom: any): Observable<any> {
    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/createReservation/${idRoom}/${token}`, body, {headers: headers});
  }

}
