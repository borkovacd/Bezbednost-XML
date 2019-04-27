
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class CityService{
  private BASE_URL = 'http://localhost:8099/api/city';

  constructor(private http: HttpClient) {

  }
  getCities(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllCities`, httpOptions);

  }
}
