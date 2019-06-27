import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SearchModel} from '../model/search.model';
import {AdvancedSearchModel} from '../model/advancedSearch.model';
import {ReservationModel} from '../model/reservation.model';
import {ReservationBackModel} from '../model/reservationBack.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class ReservationService {
  private BASE_URL = 'https://localhost:8762/resservice/api/reservations';

  constructor(private http: HttpClient) {

  }

  searchFreeRooms(object: SearchModel): Observable<any> {
    const body = JSON.stringify(object);
    return this.http.post(`${this.BASE_URL}/searchFreeRooms`, body, httpOptions);
  }

  advancedSearchFreeRooms(object: AdvancedSearchModel) : Observable<any> {
    const body = JSON.stringify(object);
    return this.http.post(`${this.BASE_URL}/advancedSearchFreeRooms`, body, httpOptions);
  }

  makeRes(resDto: ReservationModel) {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    resDto.token = token;
    const body = JSON.stringify(resDto);
    return this.http.post(`${this.BASE_URL}/createReservation`, body, {headers: headers});
  }

  getMyRes(): Observable<ReservationBackModel[]> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get<ReservationBackModel[]>(`${this.BASE_URL}/getReservationsByUser/${token}`, {headers: headers});
  }

  cancelRes(id: number): Observable<any> {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/cancelRes/${id}`,  {headers: headers});
  }

}
