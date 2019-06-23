import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable()
export  class ReservationService {
  private BASE_URL = 'https://localhost:8099/api/reservation';

  constructor(private http: HttpClient) {

  }
  getAllReservation(idAgent: string): Observable<any> {

    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': idAgent});
    return this.http.get(`${this.BASE_URL}/getAllReservations/${idAgent}`, {headers: headers});
  }

  confirmeReservation(idReservation: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/confirmReservation/${idReservation}` , {headers: headers});
  }

}
