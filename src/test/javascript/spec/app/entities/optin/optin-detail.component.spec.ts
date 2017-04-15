import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OptinDetailComponent } from '../../../../../../main/webapp/app/entities/optin/optin-detail.component';
import { OptinService } from '../../../../../../main/webapp/app/entities/optin/optin.service';
import { Optin } from '../../../../../../main/webapp/app/entities/optin/optin.model';

describe('Component Tests', () => {

    describe('Optin Management Detail Component', () => {
        let comp: OptinDetailComponent;
        let fixture: ComponentFixture<OptinDetailComponent>;
        let service: OptinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OptinDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OptinService,
                    EventManager
                ]
            }).overrideComponent(OptinDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OptinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptinService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Optin(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.optin).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
