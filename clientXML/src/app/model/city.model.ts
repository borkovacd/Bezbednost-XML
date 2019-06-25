import {CountryModel} from './country.model';

export class CityModel {

  public id: number;
  public name: string;
  public country: CountryModel;
}
