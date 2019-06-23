
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class CityService{
  private BASE_URL = 'https://localhost:8099/api/city';

  constructor(private http: HttpClient) {

  }

  getCities(): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllCities`, {headers: headers});

  }
}
