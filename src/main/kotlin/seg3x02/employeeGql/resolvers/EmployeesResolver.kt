package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver(val mongoOperations: MongoOperations, private val employeeRepository: EmployeesRepository) {
	@MutationMapping
	fun employees(@Argument id: Int): List<Employee> {
		val query = Query()
		query.addCriteria(Criteria.where("id").`is`(id))
		return mongoOperations.find(query, Employee::class.java)
	}

	@MutationMapping
	fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput) : Employee {
		val employee = Employee(input.name ?: "name", input.dateOfBirth ?: "dob", input.city ?: "city", input.salary ?: 0.0f, input.gender, input.email)
		employeeRepository.save(employee)
		return employee
	}
}
