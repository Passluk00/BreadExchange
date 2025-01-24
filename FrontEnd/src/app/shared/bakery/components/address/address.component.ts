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

  @Input() data!: Address | undefined;

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private geocodeService: MapGeocoder,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }

  center: { lng: number; lat: number } = {lat: 41.902, lng:12.488};
  zoom: number =  15;
  api: string = "AIzaSyAT1Um6CCZMeeUhZCg9HpOoaKQqvlU4sxo"
  address: string = 'Strada cerquabella 13 viterbo lazio italia';

  geocoder: any;
  map: any;


  ngOnInit() {

  //  if( isPlatformBrowser(this.platformId))
  //  this.getCordFromAddress()

    /*
    if(this.data) {
      this.address = `${this.data.street} ${this.data.number} ${this.data.city} ${this.data.country}`;
      console.error("address: "+ this.address)
    }

     */

     this.initialize()
     this.getData()

    // crea la mappa
    //  this.inizializzaMappa()


    // chiama funzione per getPosition
    //    this.fetchData()

  }


  inizializzaMappa(){
    let loader = new Loader({
      apiKey: this.api
    })

    loader.load().then(() => {
      var ele = document.getElementById('map')
      if(ele){
        new google.maps.Map(ele,{
          center: this.center,
          zoom: this.zoom,
        })
      }
    })

  }

  fetchData(){



  }




  initialize() {


    this.geocoder = new google.maps.Geocoder();
    var el = document.getElementById('map')
    var latlng = new google.maps.LatLng(-34.397, 150.644);
    const mapOption: google.maps.MapOptions = {
      zoom: 15,
      center: latlng
    }
    if (el) {
      this.map = new google.maps.Map(el, mapOption);
    }

  }



// codice semi funzionante invalid_request

  getData(){

    this.geocodeService.geocode({'address': this.address}).subscribe({
      next:(res) => {
        if(res.status == 'OK'){
          this.map.setCenter(res.results[0].geometry.location);
          var marker = new google.maps.Marker({
            map:this.map,
            position: res.results[0].geometry.location
          });

          //aggiunto

          marker.setPosition(res.results[0].geometry.location)
          marker.setMap(this.map);

        }else{
          console.error("errore:"+ res.status)
        }

      }
    })

  }

    /*

    const latlng = new google.maps.LatLng(-34.397, 150.644)
    const mapOption : google.maps.MapOptions = {
      zoom: 15,
      center:latlng
    }
    const elem = document.getElementById('map');

    if(elem){
      this.map = new google.maps.Map(elem, mapOption);
    }
    this.geocoder = new google.maps.Geocoder();
     */


  getCordFromAddress(){
    if(this.data) {
      var address: string = `${this.data.street} ${this.data.number} ${this.data.city} ${this.data.country}`;
      console.error("address: "+ address)
      const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(address)}&key=${this.api}`;

      this.http.get(url).subscribe((response: any) => {
        if (response.status === 'OK' && response.results.length > 0) {
          const loc = response.results[0].geometry.location;
          this.center = {
            lat: loc.lat,
            lng: loc.lng
          };
          this.cdr.detectChanges()
          console.error("Cordinate ottenute:" + loc.lat, loc.lng)

        } else {
          console.error("Errore nel recuperare le coordinate" + response.status)
        }
      })

    }
  }





  getCoordinate(){



  }





}




