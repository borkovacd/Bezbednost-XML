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

}
