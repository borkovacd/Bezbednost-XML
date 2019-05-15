import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {LoginModel} from '../model/login.model';
import {UserModel} from '../model/user.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class UserService {

  private BASE_URL = 'https://localhost:8443/api/user';

  constructor(private http: HttpClient) {
  }
  registration(object: UserModel): Observable<any> {
    const body = JSON.stringify(object);
    const  headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.BASE_URL}/registration`, body, {headers: headers});

  }

  login(object: LoginModel): Observable<any> {
    const body = JSON.stringify(object);
    const  headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.BASE_URL}/login`, body, {headers: headers});
  }

  getLoggedUser() {
    // return JSON.parse(localStorage.getItem('loggedUser'));
    const body = JSON.stringify(localStorage.getItem('loggedUser'));
    const  headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.BASE_URL}/loggedUser`, body, {headers: headers});

  }

  checkIfMailExists(email: string): Observable<any> {
    return this.http.get('https://localhost:8443/api/user/checkIfMailExists/' + email, httpOptions);
  }

  logout(): Observable<any> {
    localStorage.clear();
    return this.http.get('https://localhost:8443/api/user/logout' , httpOptions);

  }
}
