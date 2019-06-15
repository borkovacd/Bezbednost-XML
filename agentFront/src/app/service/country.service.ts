
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class CountryService{
  private BASE_URL = 'https://localhost:8099/api/country';

  constructor(private http: HttpClient) {

  }

  getCountry(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllCountries`, httpOptions);

  }
}
