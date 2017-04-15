import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GeoZoneDetailComponent } from '../../../../../../main/webapp/app/entities/geo-zone/geo-zone-detail.component';
import { GeoZoneService } from '../../../../../../main/webapp/app/entities/geo-zone/geo-zone.service';
import { GeoZone } from '../../../../../../main/webapp/app/entities/geo-zone/geo-zone.model';

describe('Component Tests', () => {

    describe('GeoZone Management Detail Component', () => {
        let comp: GeoZoneDetailComponent;
        let fixture: ComponentFixture<GeoZoneDetailComponent>;
        let service: GeoZoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [GeoZoneDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GeoZoneService,
                    EventManager
                ]
            }).overrideComponent(GeoZoneDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GeoZoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeoZoneService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GeoZone(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.geoZone).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
