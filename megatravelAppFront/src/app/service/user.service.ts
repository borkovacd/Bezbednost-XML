import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {LoginModel} from '../model/login.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class UserService {

  private BASE_URL = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) {
  }

  login(object: LoginModel): Observable<any> {
    const body = JSON.stringify(object);
    const  headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.BASE_URL}/login`, body, {headers: headers});
  }


}