import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SearchModel} from '../model/search.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class ReservationService {
  private BASE_URL = 'https://localhost:8089/api/reservations';

  constructor(private http: HttpClient) {

  }

  searchFreeRooms(object: SearchModel): Observable<any> {
    const body = JSON.stringify(object);
    return this.http.post(`${this.BASE_URL}/searchFreeRooms`, body, httpOptions);
  }
}
