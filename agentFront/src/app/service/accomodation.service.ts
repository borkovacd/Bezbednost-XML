import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class AccomodationService {
  private BASE_URL = 'http://localhost:8099/api/accomodation';
  constructor(private http: HttpClient) {

  }
  getAccomodation(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllAccomodation`, httpOptions);
  }

}
