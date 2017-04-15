import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GeoZoneDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/geo-zone-description/geo-zone-description-detail.component';
import { GeoZoneDescriptionService } from '../../../../../../main/webapp/app/entities/geo-zone-description/geo-zone-description.service';
import { GeoZoneDescription } from '../../../../../../main/webapp/app/entities/geo-zone-description/geo-zone-description.model';

describe('Component Tests', () => {

    describe('GeoZoneDescription Management Detail Component', () => {
        let comp: GeoZoneDescriptionDetailComponent;
        let fixture: ComponentFixture<GeoZoneDescriptionDetailComponent>;
        let service: GeoZoneDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [GeoZoneDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GeoZoneDescriptionService,
                    EventManager
                ]
            }).overrideComponent(GeoZoneDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GeoZoneDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeoZoneDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GeoZoneDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.geoZoneDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
