import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaxClassDetailComponent } from '../../../../../../main/webapp/app/entities/tax-class/tax-class-detail.component';
import { TaxClassService } from '../../../../../../main/webapp/app/entities/tax-class/tax-class.service';
import { TaxClass } from '../../../../../../main/webapp/app/entities/tax-class/tax-class.model';

describe('Component Tests', () => {

    describe('TaxClass Management Detail Component', () => {
        let comp: TaxClassDetailComponent;
        let fixture: ComponentFixture<TaxClassDetailComponent>;
        let service: TaxClassService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [TaxClassDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaxClassService,
                    EventManager
                ]
            }).overrideComponent(TaxClassDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaxClassDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxClassService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TaxClass(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.taxClass).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
