import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IntegrationModuleDetailComponent } from '../../../../../../main/webapp/app/entities/integration-module/integration-module-detail.component';
import { IntegrationModuleService } from '../../../../../../main/webapp/app/entities/integration-module/integration-module.service';
import { IntegrationModule } from '../../../../../../main/webapp/app/entities/integration-module/integration-module.model';

describe('Component Tests', () => {

    describe('IntegrationModule Management Detail Component', () => {
        let comp: IntegrationModuleDetailComponent;
        let fixture: ComponentFixture<IntegrationModuleDetailComponent>;
        let service: IntegrationModuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [IntegrationModuleDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IntegrationModuleService,
                    EventManager
                ]
            }).overrideComponent(IntegrationModuleDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntegrationModuleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntegrationModuleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IntegrationModule(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.integrationModule).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
