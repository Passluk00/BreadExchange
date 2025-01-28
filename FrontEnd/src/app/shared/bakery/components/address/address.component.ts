import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Inject,
  Input,
  OnInit,
  PLATFORM_ID,
  ViewChild
} from '@angular/core';
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {Address} from "../../../../services/models/address";
import {GoogleMap, GoogleMapsModule, MapGeocoder} from "@angular/google-maps";
import {HttpClient} from "@angular/common/http";
import {isPlatformBrowser, NgIf} from "@angular/common";
import {Loader} from "@googlemaps/js-api-loader";

@Component({
  selector: 'app-address',
  imports: [
    GoogleMapsModule,

  ],
  templateUrl: './address.component.html',
  standalone: true,
  styleUrl: './address.component.scss'
})
export class AddressComponent implements OnInit{

  @Input() id!: number | undefined;

  constructor(
    private frontEndService: FrontEndControllerService,
    private geocodeService: MapGeocoder,
  ) {
  }

  center: { lng: number; lat: number } = {lat: 41.902, lng:12.488};
  api: string = "AIzaSyAT1Um6CCZMeeUhZCg9HpOoaKQqvlU4sxo"
  address: string = '';


  geocoder: any;
  map: any;


  ngOnInit() {
    this.initialize()
    this.getPlaneAddress()

  }


  // todo controllare errore fetch dati


  getPlaneAddress(){
    if(this.id){

      this.frontEndService.getPlaneAdd({
        id: this.id
      }).subscribe({
        next: (res) => {
          if(res.address){
            this.address = res.address
          }
          this.getData()
          console.log("fetch Dati add avvenuto: "+ res);
        },
        error: (err) => {
          console.error("Errore nel Fetch Dati: ", err)
        }
      })

    }


  }



  initialize() {
    this.geocoder = new google.maps.Geocoder();
    var el = document.getElementById('map')
    const mapOption: google.maps.MapOptions = {
      zoom: 17,
      center: this.center
    }
    if (el) {
      this.map = new google.maps.Map(el, mapOption);
    }
  }

  getData(){
    this.geocodeService.geocode({'address': this.address}).subscribe({
      next:(res) => {
        if(res.status == 'OK'){
          this.map.setCenter(res.results[0].geometry.location);
          var marker = new google.maps.Marker({
            map:this.map,
            position: res.results[0].geometry.location
          });

          marker.setPosition(res.results[0].geometry.location)
          marker.setMap(this.map);

        }else{
          console.error("errore:"+ res.status)
        }
      }
    })
  }




}




