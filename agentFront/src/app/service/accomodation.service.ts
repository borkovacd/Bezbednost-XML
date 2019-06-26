import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AccomodationModel} from '../model/accomodation.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'text/plain'}),
};

@Injectable()
export class AccomodationService {
  private BASE_URL = 'https://localhost:8099/api/accomodation';
  constructor(private http: HttpClient) {

  }
  getAccomodation(): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAllAccomodations/${token}`, {headers: headers});
  }


  createAccomodation(object: AccomodationModel): Observable<any> {
    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post(`${this.BASE_URL}/createAccomodation/${token}`, body, {headers: headers});
  }

  getOneAccomodation(id: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/getAccomodation/${id}`, {headers: headers});
  }

  checkIfReservedAccomodation(id: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get(`${this.BASE_URL}/checkIfReservedAccomodation/${id}`, {headers: headers});
  }

  editAccomodation(object: AccomodationModel, id: any): Observable<any> {
    const body = JSON.stringify(object);
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.put(`${this.BASE_URL}/editAccomodation/${token}/${id}`, body, {headers: headers});
  }
  deleteAccomodation(id: any): Observable<any> {
    const token = localStorage.getItem('agentId');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.delete(`${this.BASE_URL}/deleteAccomodation/${id}/${token}`, {headers: headers});
  }


}
