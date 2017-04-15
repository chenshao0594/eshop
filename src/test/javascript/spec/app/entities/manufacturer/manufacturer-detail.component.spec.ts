import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ManufacturerDetailComponent } from '../../../../../../main/webapp/app/entities/manufacturer/manufacturer-detail.component';
import { ManufacturerService } from '../../../../../../main/webapp/app/entities/manufacturer/manufacturer.service';
import { Manufacturer } from '../../../../../../main/webapp/app/entities/manufacturer/manufacturer.model';

describe('Component Tests', () => {

    describe('Manufacturer Management Detail Component', () => {
        let comp: ManufacturerDetailComponent;
        let fixture: ComponentFixture<ManufacturerDetailComponent>;
        let service: ManufacturerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ManufacturerDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ManufacturerService,
                    EventManager
                ]
            }).overrideComponent(ManufacturerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ManufacturerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManufacturerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Manufacturer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.manufacturer).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
