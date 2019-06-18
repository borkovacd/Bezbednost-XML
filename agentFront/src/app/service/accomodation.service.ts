import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AccomodationModel} from "../model/accomodation.model";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class AccomodationService {
  private BASE_URL = 'https://localhost:8099/api/accomodation';
  constructor(private http: HttpClient) {

  }
  getAccomodation(idAgent: any): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAllAccomodations/${idAgent}`, httpOptions);
  }


  createAccomodation(object: AccomodationModel,idAgent: any): Observable<any> {
    const body = JSON.stringify(object);
    return this.http.post(`${this.BASE_URL}/createAccomodation/${idAgent}`,body,httpOptions);
  }
  getOneAccomodation(id: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.get(`${this.BASE_URL}/getAccomodation/${id}`, {headers: headers})
  }

  checkIfReservedAccomodation(id: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.get(`${this.BASE_URL}/checkIfReservedAccomodation/${id}`, {headers: headers})
  }

  editAccomodation(object: AccomodationModel, idAgent: any, id: any): Observable<any> {
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put(`${this.BASE_URL}/editAccomodation/${idAgent}/${id}`, body, {headers: headers})
  }
  deleteAccomodation(id: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.delete(`${this.BASE_URL}/deleteAccomodation/${id}`, {headers: headers});
  }


}
