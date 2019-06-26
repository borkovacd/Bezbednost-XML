
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccomodationModel} from '../model/accomodation.model';
import {AccomodationService} from '../service/accomodation.service';
import {CityService} from '../service/city.service';
import {CategoryService} from '../service/category.service';
import {TypeAccomodationService} from '../service/typeAccomodation.service';
import {AdditionalServicesService} from '../service/additionalServices.service';
import {CountryService} from '../service/country.service';

@Component ({
  templateUrl: './add-edit-accomodation.component.html'
})

export class AddEditAccomodationComponent implements OnInit{

  public form: FormGroup;
  public name: AbstractControl;
  public city: AbstractControl;
  public address: AbstractControl;
  public type: AbstractControl;
  public category: AbstractControl;
  public description: AbstractControl;
  public pic: AbstractControl;
  public aditional: AbstractControl;


  types = []
  cities = []
  categories = []
  services = []

  listAditionalService = [];
  values = '';

  public method_name = 'DODAJ';
  idagent: any;


  constructor (protected  router: Router,
               public fb: FormBuilder,
               public route: ActivatedRoute,
               public accService: AccomodationService,
               public cityService: CityService,
               public categoryService: CategoryService,
               public typeService: TypeAccomodationService,
               public additionalService: AdditionalServicesService,
               public countryService: CountryService,) {
      this.form = this.fb.group({
        'name': ['', Validators.compose([Validators.required])],
        'city': ['', Validators.compose([Validators.required])],
        'address': ['', Validators.compose([Validators.required])],
        'type': ['', Validators.compose([Validators.required])],
        'category': ['', Validators.compose([Validators.required])],
        'description': ['', Validators.compose([Validators.required])],
        'pic': ['', Validators.compose([Validators.required])],
        'aditional': ['']


      })
    this.name = this.form.controls['name'];
    this.city = this.form.controls['city'];
    this.address = this.form.controls['address'];
    this.type = this.form.controls['type'];
    this.category = this.form.controls['category'];
    this.description = this.form.controls['description'];
    this.pic = this.form.controls['pic'];
    this.aditional = this.form.controls['aditional'];

  }

  escapeCharacters(value: string): string{
    return value
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/\"/g, '&quot;')
      /*.replace(/\'/g, '&#39;')*/
      .replace(/\//g, '&#x2F;')
      .replace('src', 'drc')
      .replace(/\'/g, '&apos')

  }


  ngOnInit() {
    const mode = this.route.snapshot.params.mode;
    this.countryService.getCountry().subscribe(data => {

    })
    this.cityService.getCities().subscribe(data =>{
      this.cities = data;
    })

    this.categoryService.getCategory().subscribe(data => {
      this.categories = data;
    })

    this.additionalService.getAdditionalService().subscribe(data => {
      this.services = data;
    })

    this.typeService.getTypeAccomodation().subscribe(data => {
      this.types = data;
    })

    if (mode == 'edit') {
      this.method_name = 'IZMENI';
      const idA = this.route.snapshot.params.idA;

      this.accService.getOneAccomodation(idA).subscribe(data => {
        this.form.controls['name'].setValue(data.name);
        this.form.controls['city'].setValue(data.city.name);
        this.form.controls['address'].setValue(data.address);
        this.form.controls['type'].setValue(data.typeAccomodation.name);
        this.form.controls['category'].setValue(data.category.name);
        this.form.controls['description'].setValue(data.description);
        this.form.controls['pic'].setValue(data.pic);

        for (var i = 0; i < data.additionalServices.length; i++) {
          this.listAditionalService.push(data.additionalServices[i].name);
          this.values += data.additionalServices[i].name + '    ';
        }
      })
    } else if (mode == 'add') {
      this.method_name = 'DODAJ';
    }
  }
  confirmClick() {
    if (this.method_name === 'DODAJ') {
      this.createAccomodation();
    } else {
      this.editAccomodatin();
    }
  }

  createAccomodation(){
    const accomodation = new AccomodationModel(
      this.name.value,
      this.escapeCharacters(this.city.value),
      this.escapeCharacters(this.address.value),
      this.escapeCharacters(this.type.value),
      this.escapeCharacters(this.category.value),
      this.escapeCharacters(this.description.value),
      this.pic.value,
      this.listAditionalService,

    );
    this.idagent = localStorage.getItem('agentId');
    this.accService.createAccomodation(accomodation).subscribe(data => {
      this.router.navigateByUrl('/welcomepage' );

    })
  }
  editAccomodatin(){
    const accomodation = new AccomodationModel(
      this.name.value,
      this.escapeCharacters(this.city.value),
      this.escapeCharacters(this.address.value),
      this.escapeCharacters(this.type.value),
      this.escapeCharacters(this.category.value),
      this.escapeCharacters(this.description.value),
      this.pic.value,
      this.listAditionalService,

    );

    const idA = this.route.snapshot.params.idA;
    this.accService.editAccomodation(accomodation, idA).subscribe(data => {
      this.router.navigateByUrl('/welcomepage' );

    })
  }
  exit() {

    this.router.navigateByUrl('/welcomepage');
  }

  public check = false;

  addService(service: any) {
    this.check = false;

    if (this.listAditionalService.length === 0) {
      this.listAditionalService.push(service);
      this.values += service + '    ';
    } else {


      for (var i = 0; i < this.listAditionalService.length; i++) {

        if (this.listAditionalService[i] == service) {
          this.check = true;
          break;
        }
      }

      if (this.check == false) {
        this.listAditionalService.push(service);
        this.values += service + '    ';

      }

    }
    console.log(this.listAditionalService)
  }

  removeService(service: any) {
    this.check = false;


    for (var i = 0; i < this.listAditionalService.length; i++) {

      if (this.listAditionalService[i] == service) {
        this.listAditionalService = this.listAditionalService.filter(item => item != service);
        break;
      }
    }
    this.values = '';
    for (var i = 0; i < this.listAditionalService.length; i++) {
      this.values += this.listAditionalService[i] + '    ';
    }
  }

  }
