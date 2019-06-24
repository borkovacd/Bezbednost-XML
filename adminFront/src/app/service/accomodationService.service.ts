import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { AdditionalServiceModel } from '../model/AdditionalService.model';
import { AccomodationTypeModel } from '../model/AccomodationType.model';
import {CategoryModel} from '../model/Category.model';
import {Observable} from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class AccomodationService {

  private BASE_URL = 'https://localhost:8762/accservice/api/accomodation/addServ';
  private BASE_URL_2 = 'https://localhost:8762/accservice/api/accomodation/accType';
  private BASE_URL_3 = 'https://localhost:8762/accservice/api/accomodation/category';

  constructor(private http: HttpClient) { }


  addNewAdditionalService(additionalService: AdditionalServiceModel) {
    const data = JSON.stringify(additionalService);
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.post( `${this.BASE_URL}/addNewAdditionalService`, data, {headers: headers});
  }

  removeAdditionalService(service: AdditionalServiceModel) {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.post<AdditionalServiceModel[]>(`${this.BASE_URL}/removeAdditionalService`, service, {headers: headers});
  }

  getAdditionalServices() {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.get<AdditionalServiceModel[]>(`${this.BASE_URL}/getAdditionalServices`, {headers: headers});
  }

  addNewAccomodationType(accommodationType: AccomodationTypeModel) {
    const data = JSON.stringify(accommodationType);
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.post( `${this.BASE_URL_2}/addNewAccomodationType`, data, {headers: headers});
  }

  removeAccomodationType(type: AccomodationTypeModel) {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.post<AccomodationTypeModel[]>(`${this.BASE_URL_2}/removeAccomodationType`, type, {headers: headers});
  }

  getAccomodationTypes() {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.get<AccomodationTypeModel[]>(`${this.BASE_URL_2}/getAccomodationTypes`, {headers: headers});
  }

  addNewCategory(category: CategoryModel): Observable<any> {
    const data = JSON.stringify(category);
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.post( `${this.BASE_URL_3}/addNewCategory`, data, {headers: headers});
  }

  removeCategory(type: CategoryModel) {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.post<CategoryModel[]>(`${this.BASE_URL_3}/removeCategory`, type, {headers: headers});
  }

  getCategories() {
    const token = localStorage.getItem('loggedUser');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token' : token});
    return this.http.get<CategoryModel[]>(`${this.BASE_URL_3}/getCategories`, {headers: headers});
  }
}
