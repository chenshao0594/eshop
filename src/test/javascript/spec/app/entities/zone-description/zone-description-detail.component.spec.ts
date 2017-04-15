import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ZoneDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/zone-description/zone-description-detail.component';
import { ZoneDescriptionService } from '../../../../../../main/webapp/app/entities/zone-description/zone-description.service';
import { ZoneDescription } from '../../../../../../main/webapp/app/entities/zone-description/zone-description.model';

describe('Component Tests', () => {

    describe('ZoneDescription Management Detail Component', () => {
        let comp: ZoneDescriptionDetailComponent;
        let fixture: ComponentFixture<ZoneDescriptionDetailComponent>;
        let service: ZoneDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ZoneDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ZoneDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ZoneDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ZoneDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZoneDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ZoneDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.zoneDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
