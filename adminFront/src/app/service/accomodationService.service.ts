import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { AdditionalServiceModel } from '../model/AdditionalService.model';
import { AccomodationTypeModel } from '../model/AccomodationType.model';
import {CategoryModel} from '../model/Category.model';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AccomodationService {

  private BASE_URL = 'https://localhost:8087/api/accomodation/addServ';
  private BASE_URL_2 = 'https://localhost:8087/api/accomodation/accType';
  private BASE_URL_3 = 'https://localhost:8087/api/accomodation/category';

  constructor(private http: HttpClient) { }


  addNewAdditionalService(additionalService: AdditionalServiceModel) {
    const data = JSON.stringify(additionalService);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( `${this.BASE_URL}/addNewAdditionalService`, data, {headers: headers});
  }

  removeAdditionalService(service: AdditionalServiceModel) {
    return this.http.post<AdditionalServiceModel[]>(`${this.BASE_URL}/removeAdditionalService`, service);
  }

  getAdditionalServices() {
    return this.http.get<AdditionalServiceModel[]>(`${this.BASE_URL}/getAdditionalServices`);
  }

  addNewAccomodationType(accommodationType: AccomodationTypeModel) {
    const data = JSON.stringify(accommodationType);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( `${this.BASE_URL_2}/addNewAccomodationType`, data, {headers: headers});
  }

  removeAccomodationType(type: AccomodationTypeModel) {
    return this.http.post<AccomodationTypeModel[]>(`${this.BASE_URL_2}/removeAccomodationType`, type);
  }

  getAccomodationTypes() {
    return this.http.get<AccomodationTypeModel[]>(`${this.BASE_URL_2}/getAccomodationTypes`);
  }

  addNewCategory(category: CategoryModel): Observable<any> {
    const data = JSON.stringify(category);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( `${this.BASE_URL_3}/addNewCategory`, data, {headers: headers});
  }

  removeCategory(type: CategoryModel) {
    return this.http.post<CategoryModel[]>(`${this.BASE_URL_3}/removeCategory`, type);
  }

  getCategories() {
    return this.http.get<CategoryModel[]>(`${this.BASE_URL_3}/getCategories`);
  }
}
