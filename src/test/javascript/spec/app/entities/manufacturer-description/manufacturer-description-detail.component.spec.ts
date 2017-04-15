import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ManufacturerDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/manufacturer-description/manufacturer-description-detail.component';
import { ManufacturerDescriptionService } from '../../../../../../main/webapp/app/entities/manufacturer-description/manufacturer-description.service';
import { ManufacturerDescription } from '../../../../../../main/webapp/app/entities/manufacturer-description/manufacturer-description.model';

describe('Component Tests', () => {

    describe('ManufacturerDescription Management Detail Component', () => {
        let comp: ManufacturerDescriptionDetailComponent;
        let fixture: ComponentFixture<ManufacturerDescriptionDetailComponent>;
        let service: ManufacturerDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ManufacturerDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ManufacturerDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ManufacturerDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ManufacturerDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManufacturerDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ManufacturerDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.manufacturerDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
