
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export  class TypeAccomodationService {
  private BASE_URL = 'https://localhost:8099/api/typeAccomodation';

  constructor(private http: HttpClient){

  }
  getTypeAccomodation(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllTypes`, httpOptions);

  }

}
