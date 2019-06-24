import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SearchModel} from '../model/search.model';
import {AdvancedSearchModel} from '../model/advancedSearch.model';
import {ReservationModel} from '../model/reservation.model';

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
    const body = JSON.stringify(resDto);
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/createReservation`, body, {headers: headers});
  }

}
